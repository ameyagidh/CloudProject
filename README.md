# AWS Backend Infrastructure Project

This repository contains details about a project on AWS with three sub-repositories. The project entails a SpringBoot web application with automated CI/CD and AMI creation using HCL Packer. The cloud infrastructure is set up using CloudFormation templates, and serverless components are created using AWS Lambda functions.

## Block Diagram

The diagram below provides an overview of the system built in this project. It employs the infrastructure as code concept and is configured in a CloudFormation YAML file.

<img width="997" alt="infrastructure" src="https://github.com/ameyagidh/CloudProject/assets/65457905/9101d313-2cf6-47d0-8812-8263ee7a81d5">

## CI/CD Workflow

The diagram below illustrates the system's CI/CD workflow. The backend application, built on Spring Boot, utilizes GitHub workflows and AWS CodeDeploy for continuous integration and continuous deployment, respectively.

<img width="626" alt="cicd" src="https://github.com/ameyagidh/CloudProject/assets/65457905/6afe8952-9a7a-403a-8e5f-c37393a66221">

## Serverless Code

The following diagram presents an overview of the serverless code. The functionality for sending emails using AWS Lambda functions is achieved through serverless components.

![lambda](https://github.com/ameyagidh/CloudProject/assets/65457905/6a9b03a6-06e0-4675-bbc6-9793b7b5d10f)


