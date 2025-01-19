#!/bin/bash
echo "Started Copying the .m2 folder in order-service"
cd order-service
java Move.java
cd ..


# same for the ecommerce-service
echo "Started Copying the .m2 folder in ecommerce-service"
cd ecommerce-service
javac Move.java
java Move
cd ..


docker compose up --build