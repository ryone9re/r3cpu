FROM ubuntu:22.04

ENV RISCV=/opt/riscv
ENV PATH=$RISCV/bin:$PATH
ENV MAKEFLAGS=-j4

WORKDIR $RISCV

RUN apt update&& \
	apt install -y \
	autoconf automake autotools-dev curl libmpc-dev libmpfr-dev libgmp-dev \
	gawk build-essential bison flex texinfo gperf libtool patchutils bc \
	zlib1g-dev libexpat-dev pkg-config git libusb-1.0-0-dev device-tree-compiler \
	default-jdk gnupg vim

RUN git clone -b rvv-next --single-branch https://github.com/riscv-collab/riscv-gnu-toolchain.git && \
	cd riscv-gnu-toolchain && \
	git submodule update --init --recursive

RUN cd riscv-gnu-toolchain && mkdir build && cd build && ../configure --prefix=${RISCV} --enable-multilib && make

RUN git clone -b master --single-branch https://github.com/riscv-software-src/riscv-tests.git && \
	cd riscv-tests && \
	git submodule update --init --recursive

RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
	echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
	curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
	apt update && apt install -y sbt
