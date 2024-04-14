# Use the official Scala image from the Docker Hub
FROM hseeberger/scala-sbt:11.0.12_1.5.5_2.13.6

# Copy the current directory contents into the Docker image
COPY . /app

# Set the working directory in the Docker image
WORKDIR /app

# Compile the project
RUN sbt compile

# Run the application
CMD ["sbt", "run"]