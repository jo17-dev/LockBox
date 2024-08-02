@echo off

if exist .jo17-dev del .jo17-dev
if exist .jo17-dev-encrypted del .jo17-dev-encrypted
if exist .jo17-dev-decrypted del .jo17-dev-decrypted

javac ./Testv3.java
java Testv3