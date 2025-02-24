FROM ubuntu:22.04

# Set environment variables
ENV DEBIAN_FRONTEND=noninteractive 

# Install required dependencies
RUN apt-get update && apt-get install -y \
    wget \
    software-properties-common \
    libgtk-3-0 \
    libglib2.0-dev \
    libx11-xcb1 \
    libxcomposite1 \
    libxcursor1 \
    libxi6 \
    libxrandr2 \
    libxdamage1 \
    libxkbcommon-x11-0 \
    libgbm1 \
    x11-apps \
    && apt-get clean

# Install MySQL Workbench
RUN wget https://dev.mysql.com/get/mysql-apt-config_0.8.26-1_all.deb && \
    dpkg -i mysql-apt-config_0.8.26-1_all.deb && \
    apt-get update && \
    apt-get install -y mysql-workbench

# Create an entrypoint for MySQL Workbench
ENTRYPOINT ["/usr/bin/mysql-workbench"]