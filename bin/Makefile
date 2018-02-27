# Juwan Hong (jh3831)

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

CLASSES = \
		tls.java \
		client.java \
		server.java \
		serverThread.java \

default: classes

classes: $(CLASSES:.java=.class)

clean:
		$(RM) *.class