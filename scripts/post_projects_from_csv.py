#!/usr/bin/python
# coding: utf-8

import csv, time
import httplib, urllib, sys

if len(sys.argv) < 2:
    print "Usage: " + sys.argv[0] + " <csvfile>"
    sys.exit(1)

csvfile = sys.argv[1]

reader = csv.reader(open(csvfile, "r"), delimiter=";", quotechar='"')

projects = []

for row in reader:
    projects.append({"nome": row[0], "url": row[1]})
        
print projects

for project in projects:
    params = {"name": project["nome"], "scmUrl": project["url"]}
    #print params
    query = urllib.urlencode(params)
    
    url = "localhost:8080";
    #url = "metricminer.org.br";
    
    headers = {"Content-type": "application/x-www-form-urlencoded","Accept": "text/plain"}
    
    conn = httplib.HTTPConnection(url)
    conn.request("POST", "/MetricMiner/projects/06560fb292075c5eeca4ceb586185332", query, headers)
    response = conn.getresponse()
    print response.status, response.reason
    
    time.sleep(1)
