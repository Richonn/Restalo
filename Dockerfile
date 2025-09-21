FROM maven:3-amazoncorretto-21

WORKDIR /app

# Copy pom.xml first to leverage Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY . .

# Compile and run tests
RUN mvn compile test

# Expose port 8080
EXPOSE 8080

# Health check using the /health endpoint
HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

# Run the application
CMD ["mvn", "exec:java", "--offline"]
