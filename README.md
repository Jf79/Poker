# Poker
Repository for Software Engineering course at AIN - Semester 3 - Htwg Konstanz

![MAIN status](https://github.com/Jf79/Poker/actions/workflows/scala.yml/badge.svg)
[![codecov](https://codecov.io/gh/Jf79/Poker/branch/main/graph/badge.svg?token=63XTM75PZ2)](https://codecov.io/gh/Jf79/Poker)

## Game
This variant of poker is called video poker, known from slot machines in casinos.
You can place a bet either in LowRisk or in HighRisk.
Then you get five cards, from which you can decide whether and with which you want to play. 
If you want to play with other cards, they are replaced once.
Based on these cards, the combination is checked and any winnings are paid out.

## Compile, Run and report
Compile with `sbt compile` and run with `sbt run`
To generate a report: run with `sbt jacoco` and generate a report with `sbt jacocoReport`
