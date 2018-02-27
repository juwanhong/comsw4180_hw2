Juwan Hong (jh3831)

1. What to install on VM (Ubuntu 16.04)
	- >> sudo apt-get update
	- >> sudo apt-get install default-jdk

2. Steps used to generate RSA keys.
	- instantiated KeyPairGenerator class with "RSA"
	- generateKeyPair()
	- getPublic() and getPrivate()
	- save to files
	
	Things to note about generatring RSA keys:
		- >> java FileTransfer -k
		- This generates two public/private key pairs
		- Each pair is stored in ./server and ./client as "key.key" and "key.pub" files.

3. To run program:
	- Unzip folder "jh3831"
	
	- >> cd jh3831
	- Make sure ./client and ./server has "key.pub" and "key.key" files in them.
		- Note that public and private keys need to have the same name with differing extensions
		
	- >> make
	
	- >> java FileTransfer -s [port] [mode:(d or v)] [client rsa key path w/o extension] [server rsa key path w/o extension]
		- This runs the server
		- Note that the rsa key pairs need to have the same name and need to be in the same directory
		- This runs a threaded server. You can make multiple client connections to this server.
		
	- >> java FileTransfer -c [password:(16 char)] [file path] [mode:(a, b, or c)] [server ip] [port] [client rsa key path w/o extension] [server rsa key path w/o extension]
		- This runs the client
		- Note that the rsa key paris need to have the same name and need to be in the same directory

4. Program Architecture
	- FileTransfer.java - This is the wrapper and has the main(). This file calls client.java and server.java
	- server.java - launches serverThread.java 
	- client.java - client
	- cipher.java - contains all encyption/decryption methods


Test Case:

VM1:
>> java FileTransfer -s 5000 d ~/jh3831/client/key ~/jh3831/server/key
VM2:
>> java FileTransfer -c 1234567890123456 ~/jh3831/client/file.jpg a xxx.xxx.xxx.xxx 5000 ~/jh3831/client/key ~/jh3831/server/key

After these two scripts are run, server will print:
>>>> File received from client. File saved to: ~/jh3831/server/file
>> Listening for client connection...

Client will exit promptly after command execution.

As for other modes, the server will print:
>>>> Signature is valid.
or
>>>> Signature is invalid.
depending on the mode. Mode b will result in valid, c will result in invalid.