DESCRIPTION = "Google gRPC"
HOMEPAGE = "http://www.grpc.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0 & BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=731e401b36f8077ae0c134b59be5c906"

DEPENDS += "python3-protobuf"

SRC_URI += "file://0001-Include-missing-cstdint-header.patch \
           file://abseil-ppc-fixes.patch \
           file://0001-zlib-Include-unistd.h-for-open-close-C-APIs.patch \
           file://0001-crypto-use-_Generic-only-if-defined-__cplusplus.patch;patchdir=third_party/boringssl-with-bazel/src/ \
           file://0001-target.h-define-proper-macro-for-ppc-ppc64.patch \
           file://0001-PR-1644-unscaledcycleclock-remove-RISC-V-support.patch \
           "
SRC_URI[sha256sum] = "563588c587b75c34b928bc428548e5b00ea38c46972181a4d8b75ba7e3f24231"

RDEPENDS:${PN} = "python3-protobuf"

inherit setuptools3
inherit pypi

CFLAGS:append:libc-musl = " -D_LARGEFILE64_SOURCE"

export GRPC_PYTHON_DISABLE_LIBC_COMPATIBILITY = "1"

BORING_SSL_PLATFORM:arm = "linux-arm"
BORING_SSL_PLATFORM:x86-64 = "linux-x86_64"
BORING_SSL_PLATFORM:aarch64 = "linux-aarch64"
BORING_SSL_PLATFORM ?= "unsupported"
export GRPC_BORING_SSL_PLATFORM = "${BORING_SSL_PLATFORM}"
export GRPC_BUILD_OVERRIDE_BORING_SSL_ASM_PLATFORM = "${BORING_SSL_PLATFORM}"

BORING_SSL:arm = "1"
BORING_SSL:x86-64 = "1"
BORING_SSL:aarch64 = "1"
BORING_SSL ?= "0"
export GRPC_BUILD_WITH_BORING_SSL_ASM = "${BORING_SSL}"

GRPC_CFLAGS ?= ""
GRPC_CFLAGS:append:toolchain-clang = " -fvisibility=hidden -fno-wrapv -fno-exceptions"
export GRPC_PYTHON_CFLAGS = "${GRPC_CFLAGS}"

CLEANBROKEN = "1"

BBCLASSEXTEND = "native nativesdk"

CCACHE_DISABLE = "1"
