# %matplotlib inline
# %config InlineBackend.figure_format='retina'
# import libraries
import numpy as np
import csv
import matplotlib as mp
import pandas as pd
import matplotlib.pyplot as plt
import pandas as pd
import slideUtilities as sl
import laUtilities as ut
import seaborn as sns
from importlib import reload
from datetime import datetime
from IPython.display import Image
from IPython.display import display_html
from IPython.display import display
from IPython.display import Math
from IPython.display import Latex
from IPython.display import HTML
print('')

# Assignment 2 Exercise 1 

# 1.1

X = [] 
with open("X1.dat","r") as f:
    next(f)
    for i in f: 
        split = i.strip().split(",")
        X.append(split)
check = []
for line in X:
    for data in line:
        check.append(float(data))
#print(check)
        
mu = np.mean(check)
std = np.std(check)
final = []
for i in range(len(check)):
    if check[i] > mu +(3*std):
        final.append(check[i])
    if check[i] < mu - (3*std):
        final.append(check[i])
print(final)
print("There are "+ str(len(final)) + " outliers")

# 1.2

X2 = []
with open("X2.dat","r") as f:
    next(f)
    for i in f: 
        split = i.split()
        X2.append(split)


x = []
y = []
for line in X2:
    for point in line:
        x.append(float(line[0]))
        break
for line in X2:
    for point in line:
        y.append(float(line[1]))
        break      

for i in range(len(x)):
    norm = np.sqrt(x[i]**2 + y[i]**2)
    x[i] = x[i]/norm
    y[i] = y[i]/norm
list2 = []
for i in range(len(X2)):
    if x[i]>(np.mean(x) + (3*np.std(x))) or y[i] > (np.mean(y)+(3*np.std(y))):
        list2.append(X2[i])
    if x[i]<(np.mean(x) - (3*np.std(x))) or y[i] < (np.mean(y)-(3*np.std(y))):
        list2.append(X2[i])
print("\n")
print(list2)
print(len(list2))


from sklearn.datasets import fetch_20newsgroups

categories = ['comp.windows.x', 'comp.sys.mac.hardware','comp.graphics','sci.electronics','misc.forsale']
news_data = fetch_20newsgroups(subset='train', categories=categories)

print(news_data.target_names)
print(news_data.target)
print(news_data.filenames)

# import nltk
# nltk.download('snowball_data')
# nltk.download('stopwords')
# nltk.download('punkt')

from sklearn.feature_extraction.text import TfidfVectorizer
vectorizer = TfidfVectorizer(stop_words='english', min_df=4,max_df=0.8)
dtm = vectorizer.fit_transform(news_data.data)

print(type(dtm), dtm.shape)
terms = vectorizer.get_feature_names()
print(terms)

#Stemming data
from nltk.stem.snowball import SnowballStemmer
from nltk.tokenize import word_tokenize, sent_tokenize


stemmed_data = [" ".join(SnowballStemmer("english", ignore_stopwords=True).stem(word)  
         for sent in sent_tokenize(message)
        for word in word_tokenize(sent))
        for message in news_data.data]

stemmed_data = news_data.data

dtm = vectorizer.fit_transform(stemmed_data)
terms = vectorizer.get_feature_names()

dtm_dense = dtm.todense()
centered_dtm = dtm_dense - np.mean(dtm_dense, axis=0)
np.sum(centered_dtm,axis=0)[:,:10]

u, s, vt = np.linalg.svd(centered_dtm, full_matrices=False)

print(u.shape)
print(s.shape)
print(vt.shape)

pd.DataFrame(vt,columns=vectorizer.get_feature_names())

plt.xlim([0,50])
plt.plot(range(1,len(s)+1),s)

k=2
vectorsk = np.array(u[:,:k] @ np.diag(s[:k]))
labels = [news_data.target_names[i] for i in news_data.target]
sns.scatterplot(x=vectorsk[:,0], y=vectorsk[:, 1], hue=labels)

import seaborn as sns
k = 5
Xk = u[:,:k] @ np.diag(s[:k])
X_df = pd.DataFrame(Xk)
g = sns.PairGrid(X_df)
g.map(plt.scatter)


for i in range(6):
    top = np.argsort(vt[i])
    topterms = [terms[top[0,f]] for f in range(12)]
    print (i, topterms)
    
import seaborn as sns
sns.__version__

# '0.10.0'

# choose the top 25 columns of U for the normal space
# s[25:]=0
scopy = s.copy()
scopy[25:] = 0.
limit=0.6
N = u @ np.diag(scopy) @(vt)
O = centered_dtm - N
Onorm = np.linalg.norm(O,axis=1)
anomSet = np.where(Onorm<=limit)[0]
print(anomSet)
print(len(anomSet))

plt.plot(Onorm)
plt.plot(limit)
plt.plot(anomSet,Onorm[anomSet],'ro')
_ = plt.title("lol")

ax = ut.plotSetup(-.2,.7,-.2,.4,(8,8))
ut.centerAxes(ax)
plt.scatter([N[:,0]], [N[:,1]], color='g')
plt.scatter([centered_dtm[:,0]],[centered_dtm[:,1]], color='r')
endpoints = np.array([[-10],[10]]) @ vt[[0],:]
_ = plt.plot(endpoints[:,0], endpoints[:,1], 'g-')

