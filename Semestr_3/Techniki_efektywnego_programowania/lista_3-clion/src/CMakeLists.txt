cmake_minimum_required(VERSION 3.29)
project(lista3_clion)

set(CMAKE_CXX_STANDARD 98)

add_executable(lista3_clion main.cpp
        Interface.cpp
        Interface.h
        Tree.cpp
        Tree.h
        Node.cpp
        Node.h
        constants.cpp
        helper.cpp
)
