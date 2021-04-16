#!/usr/bin/env groovy

def call(String name = 'human') {
  echo "Hello, ${name}."
  echo "its wild this works"
  python{command("python --version")}
}
