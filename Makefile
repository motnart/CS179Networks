JAVA = javac

## Requirements for each command
SERVER_REQS = server.java

CLIENT_REQS = client.java

JDBC_REQS = jdbc.java

## Targets to compile for each command
SERVER_TARGETS = server.java

CLIENT_TARGETS = client.java

JDBC_TARGETS = jdbc.java

all: jdbc client server

## Main 
server: $(SERVER_REQS)
	$(JAVA) $(SERVER_TARGETS)

client: $(CLIENT_REQS)
	$(JAVA) $(CLIENT_TARGETS)

jdbc: $(JDBC_REQS)
	$(JAVA) $(JDBC_TARGETS)

clean:
	rm -f *~ *.o *.out Output* *.class
