# ProGBA

## RESTful-DrQA

```bash
python3 scripts/server/api.py
```


## Stanford CoreNLP Clients

The annotators of the clients need to be set first.

OpenIE annotators
```json
"annotators", "tokenize,ssplit,pos,lemma,ner,parse,coref.mention,coref"
"openie.triple.all_nominals", "true"
```

Coref annotators
```json
"annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie"
"coref.algorithm", "neural"
```



## Stanford CoreNLP Servers

First more into the directory that contains the unzipped contents of the CoreNLP
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
