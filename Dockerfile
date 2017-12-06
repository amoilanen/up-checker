# Use a Scala/Sbt runtime as a parent image
FROM hseeberger/scala-sbt

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
ADD . /app

# Make port 9000 available to the world outside this container
EXPOSE 9000

# Run app.py when the container launches
CMD ["sbt", "run"]