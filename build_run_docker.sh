#!/bin/bash
docker build -t openj9-website -f Dockerfile .
docker run -it openj9-website