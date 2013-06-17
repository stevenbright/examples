cmake_minimum_required(VERSION 2.6)
project(CMAKEDEMO)

set(CMAKE_C_FLAGS "-g -Wall")

add_subdirectory(src)

include_directories(${CMAKEDEMO_SOURCE_DIR}/src)
link_directories(${CMAKEDEMO_BINARY_DIR}/src)

#the one C file
add_executable(cdemo main)
target_link_libraries(cdemo m) #link the math library

