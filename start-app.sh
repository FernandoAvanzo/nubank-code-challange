#!/usr/bin/env bash

export CASE01='[{"operation":"buy", "unit-cost":10, "quantity": 100},{"operation":"sell", "unit-cost":15, "quantity": 50},{"operation":"sell", "unit-cost":15, "quantity": 50}]'
export CASE02='[{"operation":"buy","unit-cost":10, "quantity": 10000},{"operation":"sell","unit-cost":20, "quantity": 5000},{"operation":"sell", "unit-cost":5, "quantity":5000}]'
export CASE03='[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell","unit-cost":5, "quantity": 5000},{"operation":"sell", "unit-cost":20, "quantity":5000}]'
export CASE04='[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"buy","unit-cost":25, "quantity": 5000},{"operation":"sell", "unit-cost":15,"quantity": 10000}]'
export CASE05='[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"buy","unit-cost":25, "quantity": 5000},{"operation":"sell", "unit-cost":15,"quantity": 10000},{"operation":"sell", "unit-cost":25, "quantity": 5000}]'
export CASE06='[{"operation":"buy", "unit-cost":10, "quantity": 10000},{"operation":"sell","unit-cost":2, "quantity": 5000},{"operation":"sell", "unit-cost":20, "quantity":2000},{"operation":"sell", "unit-cost":20, "quantity": 2000},{"operation":"sell","unit-cost":25, "quantity": 1000}]'

./gradlew clean
./gradlew build
java -jar ./build/libs/nubank-code-challenge.jar "$CASE01" "$CASE02" "$CASE03" "$CASE04" "$CASE05" "$CASE06"
