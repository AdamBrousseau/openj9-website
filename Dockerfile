# docker build -t openj9-website -f dockerfile
# docker run -it openj9-website
FROM ubuntu:16.04

RUN apt-get update && apt-get install -y curl npm git
RUN curl -sL https://deb.nodesource.com/setup_10.x | bash - \
&& apt-get install -y nodejs \
&& npm install -g gatsby-cli \
&& chown -R jenkins:jenkins /.npm
RUN git config --global user.email "genie-openj9@eclipse.com" \
&& git config --global user.name "genie-openj9"
#&& git clone -b vnext https://github.com/eclipse/openj9-website.git

#WORKDIR /openj9-website
#ENTRYPOINT git pull && /bin/bash