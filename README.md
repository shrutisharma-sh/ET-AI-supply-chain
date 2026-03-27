# AI Multi-Agent Supply Chain Control Tower

We built this project to solve a real problem in supply chains — delays, lack of real-time visibility, and heavy dependence on manual decision-making.

Instead of just monitoring systems, this project focuses on building a system that can detect issues, make decisions, and act on its own.

## Overview

This is a real-time AI-powered supply chain system that:

- Monitors orders continuously  
- Detects issues like delays or stuck orders  
- Uses AI to decide what action should be taken  
- Automatically executes actions  
- Maintains logs for full traceability  

The goal is to transform a traditional supply chain into a self-operating system.

## Core Idea

Most systems today are reactive. They show problems but rely on humans to fix them.

This system follows a closed loop:

Detect → Decide → Act → Log → Repeat

It not only identifies problems but also resolves them automatically.

## System Components

The system is built using multiple agents, each with a clear responsibility.

### Order Agent
Handles order creation and lifecycle tracking with statuses like PLACED, SHIPPED, and DELIVERED.

### Failure Detection Agent
Detects delayed orders, stuck workflows, and risk conditions.

### Decision Agent
Uses Hugging Face API to analyze the situation and suggest actions such as:
- Reorder
- Escalate
- Switch supplier
- Mark priority

### Action Agent
Executes the decisions by updating the database, changing priorities, or re-triggering processes.

### Audit Agent
Logs every step including the issue, decision, action, and timestamp.

## System Workflow

1. An order is created  
2. The system continuously monitors it  
3. If an issue is detected, it is flagged  
4. AI generates a decision  
5. The action is executed automatically  
6. All steps are logged for traceability  

## AI Integration

The system integrates with Hugging Face API for decision-making.

- Input is sent as a prompt describing the issue  
- AI returns a suggested action  
- The response is parsed into structured decisions  

A rule-based fallback ensures the system remains reliable even if the AI fails.

## Features

- Real-time monitoring  
- Autonomous decision execution  
- Dashboard with insights and alerts  
- Manual and automatic execution modes  
- Explainable AI reasoning  
- Audit logs for full traceability  

## Reliability and Safety

To ensure controlled behavior:

- Decisions are validated before execution  
- Rule-based fallback is implemented  
- All actions are logged  

## Tech Stack

Frontend: React (Vite)  
Backend: Spring Boot  
AI: Hugging Face API  
Database: MySQL  
Authentication: JWT  

## Running the Project

### Backend

cd supply-chain  
mvn spring-boot:run  

### Frontend

cd frontend  
npm install  
npm run dev  

## Demo Flow

1. Create an order  
2. Open the dashboard  
3. Run AI manually or enable auto mode  
4. System detects an issue  
5. AI suggests a decision  
6. Action is executed  
7. Logs are generated  

## Business Impact

- Reduces supply chain delays  
- Improves response time  
- Minimizes manual intervention  
- Helps prevent operational losses  

## Future Improvements

- Integration with real-time logistics APIs  
- Predictive analytics using ML models  
- IoT-based tracking  
- Advanced dashboards  

## Final Statement

This project demonstrates how supply chains can evolve from reactive systems into intelligent, autonomous systems capable of real-time decision-making and execution.
