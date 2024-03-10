# Infrastructure

## Prerequisites

Before using this project, ensure that you have an **AWS account** and have set up the **AWS CLI**.

To set up the AWS CLI, refer to the [official documentation](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).

## CloudFormation

Refer to the [AWS CloudFormation documentation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/template-anatomy.html) to understand the anatomy of CloudFormation templates.

## Environment Variables or VM Arguments

Set the following environment variables based on the region:

### For the "dev" region:

```bash
export AWS_PROFILE=dev
export AWS_REGION=us-east-1
```

### For the "demo" region:

```bash
export AWS_PROFILE=demo
export AWS_REGION=us-east-1
```

## Commands to Set Up the VPC

Use the following commands to set up the VPC instance:

### Generic Command

```bash
aws cloudformation create-stack --stack-name <stack-name> --template body \
file://<filename> --parameters \
ParameterKey=VPCCidrBlock,ParameterValue="<IP>" \
ParameterKey=CIDRBlocSubnet1,ParameterValue="<IP>" \
ParameterKey=CIDRBlocSubnet2,ParameterValue="<IP>" \
ParameterKey=CIDRBlocSubnet3,ParameterValue="<IP>" \
ParameterKey=AvailabilityZone1,ParameterValue="<availabilityZone(a,b,c)>" \
ParameterKey=AvailabilityZone2,ParameterValue="<availabilityZone(a,b,c)>" \
ParameterKey=AmiImageId,ParameterValue="<ami-id>" \
ParameterKey=AvailabilityZone3,ParameterValue="<availabilityZone(a,b,c)>" \
--region <region>
```

### Demo for Multiple VPCs in the Same Region

To set up the first instance:

```bash
aws cloudformation create-stack --stack-name demovpc-1 --template-body file://mywebapp-infra.yml --parameters ParameterKey=DNSDomain,ParameterValue="prod.ebenezerwilliams.me." ParameterKey=EnvRunning,ParameterValue="prod" ParameterKey=AmiImageId,ParameterValue="ami-05ae6c450a784f9c6" ParameterKey=VPCCidrBlock,ParameterValue="10.2.0.0/16" ParameterKey=CIDRBlocSubnet1,ParameterValue="10.2.1.0/24" ParameterKey=CIDRBlocSubnet2,ParameterValue="10.2.2.0/24" ParameterKey=CIDRBlocSubnet3,ParameterValue="10.2.3.0/24" ParameterKey=AvailabilityZone1,ParameterValue="a" ParameterKey=AvailabilityZone2,ParameterValue="b" ParameterKey=AvailabilityZone3,ParameterValue="c" --region us-east-1 --capabilities CAPABILITY_NAMED_IAM
```

To set up the second instance:

```bash
aws cloudformation create-stack --stack-name demovpc-2 --template-body file://mywebapp-infra.yml --parameters ParameterKey=DNSDomain,ParameterValue="prod.ebenezerwilliams.me." ParameterKey=EnvRunning,ParameterValue="prod" ParameterKey=AmiImageId,ParameterValue="ami-05ae6c450a784f9c6" ParameterKey=VPCCidrBlock,ParameterValue="10.2.0.0/16" ParameterKey=CIDRBlocSubnet1,ParameterValue="10.2.1.0/24" ParameterKey=CIDRBlocSubnet2,ParameterValue="10.2.2.0/24" ParameterKey=CIDRBlocSubnet3,ParameterValue="10.2.3.0/24" ParameterKey=AvailabilityZone1,ParameterValue="a" ParameterKey=AvailabilityZone2,ParameterValue="b" ParameterKey=AvailabilityZone3,ParameterValue="c" --region us-east-1 --capabilities CAPABILITY_NAMED_IAM
```

### Delete S3

Use the following command to delete the S3 instance:

```bash
aws s3 rm s3://1d089840-9ff1-11ec-94b9-125e1d1edc59.dev.ebenezerwilliams.me --recursive
```

### Delete the Servers

```bash
aws cloudformation delete-stack --stack-name demovpc-1 --region us-east-1
```

```bash
aws cloudformation delete-stack --stack-name demovpc-2 --region us-east-1
```

### Demo for Multiple VPCs in Different Regions

To set up the first instance in us-east-1 (N. Virginia):

```bash
aws cloudformation create-stack --stack-name demovpc-1 --template-body file://mywebapp-infra.yml --parameters ParameterKey=DNSDomain,ParameterValue="prod.ebenezerwilliams.me." ParameterKey=EnvRunning,ParameterValue="prod" ParameterKey=AmiImageId,ParameterValue="ami-0535925c33eb26de3" ParameterKey=VPCCidrBlock,ParameterValue="10.2.0.0/16" ParameterKey=CIDRBlocSubnet1,ParameterValue="10.2.1.0/24" ParameterKey=CIDRBlocSubnet2,ParameterValue="10.2.2.0/24" ParameterKey=CIDRBlocSubnet3,ParameterValue="10.2.3.0/24" ParameterKey=AvailabilityZone1,ParameterValue="a" ParameterKey=AvailabilityZone2,ParameterValue="b" ParameterKey=AvailabilityZone3,ParameterValue="c" --region us-east-1 --capabilities CAPABILITY_NAMED_IAM
```

To

 set up the second instance in us-east-2 (Ohio):

```bash
aws cloudformation create-stack --stack-name demovpc-2 --template-body file://mywebapp-infra.yml --parameters ParameterKey=AmiImageId,ParameterValue="ami-0000" ParameterKey=VPCCidrBlock,ParameterValue="10.2.0.0/16" ParameterKey=CIDRBlocSubnet1,ParameterValue="10.2.1.0/24" ParameterKey=CIDRBlocSubnet2,ParameterValue="10.2.2.0/24" ParameterKey=CIDRBlocSubnet3,ParameterValue="10.2.3.0/24" ParameterKey=AvailabilityZone1,ParameterValue="a" ParameterKey=AvailabilityZone2,ParameterValue="b" ParameterKey=AvailabilityZone3,ParameterValue="c" --region us-east-2
```

### Describe Stacks

Use the following command to check the server instances at any point:

```bash
aws cloudformation describe-stacks
```

### Create CI/CD Stack

Use the following command to create the CI/CD stack:

```bash
aws cloudformation create-stack --stack-name codedeploystack --template-body file://ci-cd-infra.yml --parameters ParameterKey=CodeDeployBucketName,ParameterValue="codedeploy.ebenezerwilliams.me" --region us-east-1 --profile=demo --capabilities CAPABILITY_NAMED_IAM
```

### Update Stack

Use the following command to update the server instances at any point:

```bash
aws cloudformation deploy --stack-name demovpc-1 --template-file mywebapp-infra.yml --capabilities CAPABILITY_NAMED_IAM
```