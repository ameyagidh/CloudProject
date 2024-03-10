# AWS Backend Infrastructure Project

This repository contains details about a project on AWS with three sub-repositories. The project entails a SpringBoot web application with automated CI/CD and AMI creation using HCL Packer. The cloud infrastructure is set up using CloudFormation templates, and serverless components are created using AWS Lambda functions.

## Block Diagram

The diagram below provides an overview of the system built in this project. It employs the infrastructure as code concept and is configured in a CloudFormation YAML file.

[Insert Block Diagram Image]

## CI/CD Workflow

The diagram below illustrates the system's CI/CD workflow. The backend application, built on Spring Boot, utilizes GitHub workflows and AWS CodeDeploy for continuous integration and continuous deployment, respectively.

[Insert CI/CD Workflow Image]

## Serverless Code

The following diagram presents an overview of the serverless code. The functionality for sending emails using AWS Lambda functions is achieved through serverless components.

[Insert Serverless Code Image]