FROM node:14.17.5

WORKDIR /app

COPY package.json /app/package.json

RUN npm install
RUN npm install -g @angular/cli@12.2.2

CMD ng serve --host 0.0.0.0