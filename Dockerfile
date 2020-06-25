# docker build -t openj9-website -f dockerfile
# docker run -it openj9-website
FROM ubuntu

RUN apt update && apt install -y curl && curl -sL https://deb.nodesource.com/setup_10.x | sh
RUN apt install -y nodejs && node --version
RUN curl -L https://npmjs.org/install.sh | sh
RUN npm install -g gatsby-cli
RUN apt install -y git
RUN git config --global user.email "genie-openj9@eclipse.com" && git config --global user.name "genie-openj9"
RUN git clone -b vnext https://github.com/eclipse/openj9-website.git

WORKDIR /openj9-website
ENTRYPOINT git pull && node --version && npm install && npm run deploy && /bin/bash