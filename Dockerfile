# docker build -t openj9-website -f dockerfile
# docker run -it openj9-website
FROM ubuntu:16.04

RUN apt update && apt install -y curl npm git
RUN curl -sL https://deb.nodesource.com/setup_10.x | bash -
RUN apt install -y nodejs \
&& node --version
RUN npm install -g gatsby-cli
RUN git config --global user.email "genie-openj9@eclipse.com" \
&& git config --global user.name "genie-openj9"
RUN git clone -b vnext https://github.com/eclipse/openj9-website.git

WORKDIR /openj9-website
ENTRYPOINT git pull && /bin/bash