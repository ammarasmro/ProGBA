# Progressive Graph Builder API (ProGBA)
A question-answering natural language processing (NLP) API

## Motivation
SciFi virtual assistants, like Jarvis, perform many mindblowing tasks effortlessly. Like flying a robotic suit, identifying hostiles, planning the optimum path. As science advances, and AI reaches new heights, those goals are no longer a fantasy. Alot of those systems already exist, with DIY tutorials all over the internet. However, there is still an iceberg that is underestimated. Jarvis could:
 * Have a decent conversation with a human being
 * Stay consistent around the main topic
 * Have open-ended conversations
 * Achieve or aid the human to achieve a goal
 * Identify and engage in sarcasm
 
How can a chatbot keep track of the information exchange in a way that makes it possible to have:
1. _**Meaningful**_ conversations 
2. _**Fruitful**_ conversations 

This project aims to tackle this issue by changing textual data into a knowledge representation that makes the above mentioned points more feasible. 

## Definition
ProGBA is an API that is designed to power a chatbot and give it the ability to represent acquired knowledge as a graph. It can be thought of as a text-to-graph microservice. It takes in users' utterances, puts textual data through a processing pipeline, extracts triples, and persists them to a graph database. 


### Triple Formation
The first section of the sentence processing pipeline is the IBM Watson NLU API. Sentences are sent to the API and it returns a JSON response. This API returns several several valuable features.
1. Keywords: words that are directly extracted from text.
2. Concepts: more abstract keywords that don't necessarily exist within text. Those are linked to DBPedia resources.
3. Triples

The second section uses the OpenIE project of Stanford CoreNLP. This tool splits the sentence at several points to create triples. In this section, we favour quantity over quality. There are many smaller triples that are not returned from the IBM NLU. One valuable type of triples that are extracted here are the word-to-word triples. For example, "Energy is electric" is objtained from "Electrical energy". Those triples, while not providing comprehensive definitions, can carry useful information.

The last part of the pipeline uses part-of-speech tagging and rule based techniques to form logically correct triples. From experiments, this section produced better quality content than the former ones. For this reason, it was placed at the end to be used as a fact checking machine.

## Goals
- [ ] Develop a grammar checker to use that as a metric to measure the quality of the produced content
- [ ] Store documents with their data in a MongoDB instance
- [ ] Log conversations to a MySQL server

## Tech Stack
* Programming Languages: Java 8 and Python 3.6
* Database: Neo4j & MongoDB (In progress)
* NLP Tools & Systems: Stanford CoreNLP, IBM Watson, OpenNLP, and DrQA
* Utilities: Spark Java, Gson, and Guava

## Setup and Usage

### RESTful-DrQA

```bash
python3 scripts/server/api.py
```


### Stanford CoreNLP Clients

The annotators of the clients need to be set first.

OpenIE annotators
```
"annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref.mention,coref"
"openie.triple.all_nominals", "true"
```

Coref annotators
```
"annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie"
"coref.algorithm", "neural"
```



### Stanford CoreNLP Servers

First move into the directory that contains the unzipped contents of the CoreNLP
```bash
$ pwd
/Users/ammarasmro/Software/NLP/stanford-corenlp-full-2018-02-27
```

Run the servers using
```bash
# For the Open IE annotators
java -mx4g -cp "*" edu.stanford.nlp.pipeline.StanfordCoreNLPServer -port 8000 -timeout 15000

# For the Coref annotators
java -mx4g -cp "*" edu.stanford.nlp.pipeline.StanfordCoreNLPServer -port 9000 -timeout 15000
```

### Neo4j

### MongoDB
To start an instance
```bash
mongod --replSet "rs"
# Then, from another terminal window
mongo
> rs.initiate()
```