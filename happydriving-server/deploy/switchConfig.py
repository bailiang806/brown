# encoding: utf-8

import re, os, shutil
import ConfigParser


Config = ConfigParser.ConfigParser()

curEnv = "online"  # dev | online

def ConfigSectionMap(section):
    rtn = {}
    options = Config.options(section)
    for option in options:
        try:
            rtn[option] = Config.get(section, option)
        except:
            print("exception on %s!" % option)
            rtn[option] = None
    return rtn


def doReplace(filepath, reg, replacement):
    if not os.path.isfile(filepath):
        raise Exception("filepath=["+str(filepath)+"] does NOT exist.")

    # curDir = os.path.dirname(os.path.realpath(__file__))

    tmpfile = filepath+".tmp"
    print tmpfile
    with open(tmpfile, "w") as fw:
        with open(filepath, "r") as fr:
            while True:
                line = fr.readline()
                if not line:
                    break
                line = re.sub(reg, replacement, line)
                fw.write(line)

    os.remove(filepath)
    shutil.move(tmpfile, filepath)




if __name__ == '__main__':
    Config.read("deploy.ini")
    for section in Config.sections():
        if section.startswith(curEnv):
            curSection = ConfigSectionMap(section)
            filepath        = curSection['filepath']
            reg             = curSection['regex']
            replacement     = curSection['replacement']
            doReplace(filepath, reg, replacement)








