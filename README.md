# Schedoscope

## Introduction

Schedoscope is a scheduling framework for painfree agile development, testing, (re)loading, and monitoring of your datahub, lake, or whatever you choose to call your Hadoop data warehouse these days.

With Schedoscope,
* you never have to create schema DDL and migration scripts;
* you never have to manually determine which data must be deleted and recomputed in face of retroactive changes to logic or data structures;
* you specify Hive table structures (called "views"), partitioning schemes, storage formats, dependent views, as well as the transformation logic in one file in a concise Scala DSL;
* you have a wide range of options for expressing data transformations - from file operations and MapReduce jobs to Pig scripts, Hive queries, and Oozie workflows;
* you benefit from your Scala IDE's code completion and have less typos hitting you during deployment;
* you can easily write tests for your transformation logic and run them quickly;
* you schedule jobs by expressing the views you need - Schedoscope takes care that all dependencies are computed as well;
* you achieve a higher utilization of your YARN cluster's resources because job launchers are not YARN applications themselves that consume YARN capactity.

## Tutorials

Please follow the Open Street Map tutorial to install, compile, and run Schedoscope in a standard Hadoop distribution image within minutes:

- [Open Street Map Tutorial](Open Street Map Tutorial)

## Implementing Views
- [Setting up a Schedoscope Project](Setting up a Schedoscope Project)
- [Schedoscope View DSL](Schedoscope View DSL)
- [Storage formats](Storage Formats)
- Transformations
 - [NoOp](NoOp Transformations)
 - [File System](File System Transformations)
 - [Hive](Hive Transformations)
 - [Pig](Pig-Transformations)
 - [MapReduce](MapReduce Transformations)
 - [Morphline](Morphline Transformations)
- [Test Framework](Test Framework)

## Operating Schedoscope
- [Bundling and Deploying](Bundling and Deploying)
- [Starting](Starting Schedoscope)
- [Scheduling](Scheduling)
- [REST API](Schedoscope REST API)
- [Command Reference](Command Reference)
- [View Pattern Reference](View Pattern Reference)

## Extending Schedoscope
- [Architecture](Architecture)

## Core Team
* [Utz Westermann](https://github.com/utzwestermann) (Otto Group): Maintainer, DSL concept and implementation 
* [Hans-Peter Zorn](https://github.com/hpzorn) (Inovex GmbH): Scheduling architecture and implementation, Morphline support
* [Dominik Benz](https://github.com/dominikbenz) (Inovex GmbH): Test framework, deployment system, transformations, command shell

Please help making Schedoscope a better place!

## License
Licensed under the [Apache License 2.0](https://github.com/ottogroup/schedoscope/blob/master/LICENSE)
