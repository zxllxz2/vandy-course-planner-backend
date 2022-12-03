import re
from selenium import webdriver
from selenium.webdriver.chrome.service import Service as ChromeService
from webdriver_manager.chrome import ChromeDriverManager
from time import sleep
import pandas as pd

def lambda_handler(event, context):

    prof = pd.read_csv("./Data/Professor_name.csv")

    service = ChromeService(executable_path=ChromeDriverManager().install())
    option = webdriver.ChromeOptions()
    option.add_argument('lang=en')
    driver = webdriver.Chrome(options=option, service=service)

    # Get RMP scores; might have university mismatch; need inspection afterward
    rmp_scores = []
    for person in prof.Name:
        xp = re.sub("\\s+", "%20", person)
        url = "https://www.ratemyprofessors.com/search/teachers?query=" + xp + "&sid=4002"
        driver.get(url)
        sleep(3)

        pageSource = driver.page_source
        lxml_soup = BeautifulSoup(pageSource, 'lxml')
        body = lxml_soup.find("div", id="root")
        cards = body.find_all("a")

        for card in cards:
            flag = False
            for cla in card.get_attribute_list("class"):
                if cla is None or "Card" not in cla: continue
                temp = [card["href"]]
                for each in card.div.strings:
                    if each.strip() != "": temp.append(each.strip())
                rmp_scores.append(temp)
                flag = True
                break
            if flag: break
            
    # Clean the parsed data
    prof_rmp_meta_clean = []
    for each in rmp_scores:
        temp = [each[0][each[0].index("=")+1:]]
        temp.append(each[2])
        temp.append(each[4])
        temp.append(each[6])
        temp.append(each[9])
        prof_rmp_meta_clean.append(temp)

    score_schema = ["Tid", "Over_rate", "Name", "college", "Diff_rate"]
    df_score_2 = pd.DataFrame(prof_rmp_meta_clean, columns = score_schema).reset_index(drop=True)
    df_score_2.to_csv("Prof_clean1.csv", index=False)

    ratings1 = pd.read_csv("Prof_clean1.csv")

    # Find those professors without RMP card
    not_appear = []
    see = set(ratings1.Name)
    for i in prof.Name:
        if i not in see:
            not_appear.append(i)
    not_appear

    # Generate professor csv
    no_app = []
    for each in not_appear:
        temp = ["", 0, each, "Vanderbilt University", 0]
        no_app.append(temp)

    df_score_new = ratings1.append(pd.DataFrame(no_app, columns=score_schema)).reset_index(drop=True)
    df_score_new = df_score_new.drop("college", axis=1)
    df_score_new.to_csv("Professor.csv", index=False)