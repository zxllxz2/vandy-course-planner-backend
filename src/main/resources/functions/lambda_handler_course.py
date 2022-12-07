# Author: Toby Zhu
# Lambda handler function for parsing course data

import requests
from bs4 import BeautifulSoup
import pandas as pd
import my_fake_useragent as ua

def lambda_handler(event, context):

    # Get overall course information page
    page = requests.get("https://www.coursicle.com/vanderbilt/courses/CS/")
    soup = BeautifulSoup(page.text, "lxml")
    container = soup.find_all(id="tileContainer")
    courses = container[0].find_all("a", class_="tileElement")

    # Parse course name, number, and link to specific course page
    course_list = []
    for course in courses[:-1]:
        temp = course.span.text.split() + [course.div.text, "https://www.coursicle.com/vanderbilt/courses/CS/" + course["href"]]
        course_list.append(temp)

    # Set user_agent generator
    user_agent = ua.UserAgent()

    # Parse course detail from every course page
    new_course_list = []
    for item in course_list:
        page_in = requests.get(item[3], headers=generate_headers(user_agent)).text
        soup2 = BeautifulSoup(page_in, "lxml")
        detail_container = soup2.body.find_all("div", id="subItemContainer")
        detail_courses = detail_container[0].find_all("div", class_="subItemLabel")
        temp = []
        terms = None
        for each in detail_courses:  
            if "rofessor" in each.text:
                temp.append(list(map(lambda x:x.text, each.parent.find_all("a", class_="professorLink"))))
            if "emester" in each.text:
                terms = each.parent.find_all("div", class_="subItemContent")[0].text
        temp.append(terms)
        new_course_list.append(item + temp)

    df = pd.DataFrame(new_course_list, columns = ["Subject", "Number", "Name", "Link", "Professors", "Frequency"]).drop('Link', axis=1)
    # Special topics from Fall 2021 to Spring 2023
    special = [
        ['CS', '103891', 'Special Topics - Numerical Methods for CS', ['David Hyde'], 'Fall 2022'],
        ['CS', '113891', 'Special Topics - Scalable Microservices', ['Douglas Schmidt'], 'Spring 2023'],
        ['CS', '123891', 'Special Topics - The Algorithms of Robotics', ['Jie Ying Wu'], 'Spring 2023, Spring 2022'],
        ['CS', '133891', 'Special Topics - Computational Creativity', ['Douglas Fisher'], 'Spring 2023'],
        ['CS', '143891', 'Special Topics - Reinforcement Learning', ['Gautam Biswas'], 'Fall 2022, Fall 2021'],
        ['CS', '153891', 'Special Topics - Mach Lrn / Nat Lang Proc Hlthc', ['Zhijun Yin'], 'Spring 2023, Spring 2022'],
        ['CS', '163891', 'Special Topics - Reverse Engineering', ['Daniel Balasubramanian'], 'Spring 2022'],
        ['CS', '173891', 'Special Topics - Computing and the Environment', ['Douglas Fisher'], 'Fall 2021'],
        ['CS', '183891', 'Special Topics - Fndatns Human/Computer Intract', ['Shilo Anders'], 'Fall 2021'],
        ['CS', '193891', 'Special Topics - Network Analysis in Healthcare', ['You Chen'], 'Fall 2022, Fall 2021'],
        ['CS', '123892', 'Special Topics - Projects in Machine Learning', ['Ipek Oguz'], 'Spring 2023, Spring 2022'],
        ['CS', '143892', 'Special Topics - Autonomous Vehicles', ['Laine Forrest'], 'Spring 2023, Spring 2022'],
    ]

    # Drop rows of graduate courses and incorrect course info about special topics
    df_new = df.drop(df[(df.Number == '3891') | (df.Number == '3892')].index).loc[df['Number'] < "5000"]
    # Append correct course info of special topics
    df_new_full = df_new.append(pd.DataFrame(special, columns=df_new.columns.values.tolist())).reset_index()
    df_new_full.drop("Professors", axis=1).to_csv("Course.csv", index=False)

    # Get all professors teaching courses
    professors = set()
    for c in new_course_list:
        professors.update(c[4])
    all_professor = list(professors)

    # Generate csv of professor names
    df2 = pd.DataFrame(all_professor, columns =["Name"]).reset_index(drop=True)
    df2.to_csv("Professor_name.csv", index=False)

    # Generate teaching information of undergraduate courses
    teaching = []
    for c in new_course_list:
        if c[1] == '3891' or c[1] == '3892' or c[1] > '5000':
            continue
        for prof in c[4]:
            temp = c[:2]
            temp.append(prof)
            teaching.append(temp)

    # Add special topics in teaching
    for each in special:
        teaching.append(each[:2] + each[3])

    # Generate teaching csv with professor names
    df3 = pd.DataFrame(teaching, columns =["Subject", "Course_no", "Professor"]).reset_index(drop=True)
    df3.to_csv("Teaching_name.csv", index=False)



def generate_headers(user_agent):
  paramss = {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
    "Accept-Encoding": "gzip, deflate, br",
    "Accept-Language": "en,zh-CN;q=0.9,zh;q=0.8",
    "Cache-Control": "max-age=0",
    "Connection": "keep-alive",
    "Host": "www.coursicle.com",
    "Referer": "https://colab.research.google.com/",
    "sec-ch-ua": "\"Chromium\";v=\"106\", \"Google Chrome\";v=\"106\", \"Not;A=Brand\";v=\"99\"",
    "sec-ch-ua-mobile": "?0",
    "sec-ch-ua-platform": "Windows",
    "Sec-Fetch-Dest": "document",
    "Sec-Fetch-Mode": "navigate",
    "Sec-Fetch-Site": "none",
    "Sec-Fetch-User": "?1",
    "Upgrade-Insecure-Requests": "1",
    "User-Agent": user_agent.random().strip()
  }
  return paramss