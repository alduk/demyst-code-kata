# demyst-code-cata

## Description

This is a command-line tool written in Scala that consumes the API at https://api.ipify.org/?format=json and returns just the IP address part of it.

## Requirements

- Scala
- sbt
- Docker (optional)

## Installation

Clone the repository:

```bash
git clone https://github.com/yourusername/demyst-code-kata.git
cd demyst-code-kata
```

## Usage

To run the program:

```bash
sbt run
```

## Docker

To build the Docker image:

```bash
docker build -t demyst-code-kata .
```

To run the Docker container:

```bash
docker run -it demyst-code-kata
```

## Testing

To run the tests:

```bash
sbt test
```