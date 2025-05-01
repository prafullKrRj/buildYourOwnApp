# 🏢 PropVault

## Real Estate Customer Relationship Management Application

![PropVault](https://img.shields.io/badge/PropVault-blue)
![Android](https://img.shields.io/badge/Platform-Android-brightgreen)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-orange)
![Firebase](https://img.shields.io/badge/Backend-Firebase-yellow)
![Version](https://img.shields.io/badge/Version-1.0.0-lightgrey)

<p align="center">
  <img src="https://raw.githubusercontent.com/prafullKrRj/buildYourOwnApp/refs/heads/main/PropVault/app/src/main/res/drawable/real_estate.png" alt="PropVault Banner" width="200"/>
</p>

## 📱 What It Does

**PropVault** is a comprehensive real estate management solution designed for property development companies. The application streamlines the process of managing developments, buildings, units, deals, and payments through an intuitive interface. It features:

* **Single Admin Portal**: Centralized management system for property administrators  
* **Multiple Customer Access**: Allowing potential buyers to browse available properties  
* **End-to-End Deal Management**: From property listing to payment tracking  
* **Real-time Updates**: Using Firebase for instant data synchronization  

### Key Features

#### For Admin (Single Access)

- **Comprehensive Development Management**: Create and manage multiple real estate developments  
- **Hierarchical Building Structure**: Organize buildings within developments  
- **Unit Management**: Track property units with detailed information and pricing  
- **Deal Tracking**: Monitor all property deals from initiation to closing  
- **Payment Processing**: Keep track of all payment transactions  

#### For Customers (Multiple Users)

- **Property Exploration**: Browse available developments and units  
- **Detailed Unit Information**: Access comprehensive details about potential properties  
- **Streamlined Viewing Experience**: Intuitive navigation through property listings  

## 🛠️ How It's Built

### Architecture

**PropVault** follows a multi-module architecture with clean separation of concerns:

- **:app**: Main application module that connects all components  
- **:admin**: Contains admin-specific features and interfaces  
- **:common**: Shared models, utilities, and core logic  
- **:customer**: Customer-facing screens and features  

### How to use

For Admin: use "admin" as username and "password" as password  
For Customer: Either create account or login using username: "customer", password: "password"  

### Tech Stack

- **Language**: 100% Kotlin  
- **UI Framework**: Jetpack Compose  
- **Architecture**: MVVM + Clean Architecture  
- **Backend**: Firebase (Firestore, Authentication)  
- **Async Operations**: Kotlin Coroutines with Flow  
- **Dependency Injection**: Koin  
- **Navigation**: Jetpack Navigation Compose  

### Core Models

The application is built around five main data models:

1. **Development**: Real estate development projects  
2. **Building**: Structures within developments  
3. **PropertyUnit**: Individual units available for purchase  
4. **Deal**: Sales transactions with customers  
5. **Payment**: Financial transactions related to deals  

### Application Flow

```mermaid
graph TD
    %% Project Module Structure
    subgraph ProjectModules["Project Modules"]
        AppModule[":app - Main Application"]
        CommonModule[":common - Models & Shared Logic"]
        AdminModule[":admin - Admin Features"]
        CustomerModule[":customer - Customer Features"]
        
        AppModule --> CommonModule
        AppModule --> AdminModule
        AppModule --> CustomerModule
        AdminModule --> CommonModule
        CustomerModule --> CommonModule
    end

    %% Admin Module Flow
    subgraph AdminModule["Admin Module Flow"]
        AdminHome[Admin Home]
        
        %% Development Flow
        DevList[Developments List]
        BuildingList[Buildings List]
        UnitsList[Units List]
        UnitScreen[Unit Details Screen]
        AddUnitScreen[Add Unit Screen]
        EditUnitScreen[Edit Unit Screen]
        
        %% Payments Flow
        AdminPayments[Payments Screen]
        
        %% Deals Flow
        DealsListScreen[Deals List]
        DealDetailsScreen[Deal Details Screen]
        
        AdminHome --> AdminPayments
        AdminHome --> DevList
        AdminHome --> DealsListScreen
        
        DevList --> BuildingList
        BuildingList --> UnitsList 
        UnitsList --> UnitScreen
        UnitsList --> AddUnitScreen
        UnitScreen --> EditUnitScreen
        
        DealsListScreen --> DealDetailsScreen
    end
    
    %% Customer Module Flow
    subgraph CustomerModule["Customer Module Flow"]
        CustomerHome[Customer Home]
        PropertyListScreen[Properties List]
        UnitDetailsScreen[Unit Details]
        
        CustomerHome --> PropertyListScreen
        PropertyListScreen --> UnitDetailsScreen
    end
    
    %% Common Module - Models
    subgraph CommonModule["Common Module - Models"]
        Development[Development.kt]
        Building[Building.kt]
        PropertyUnit[PropertyUnit.kt]
        Deal[Deal.kt]
        Payment[Payment.kt]
        
        Development --- Building
        Building --- PropertyUnit
        PropertyUnit --- Deal
        Deal --- Payment
    end
```

## ⚠️ Limitations

- Currently limited to Android platform only  
- Offline functionality is limited; requires internet connection for most features  
- No multi-language support at this stage  
- Limited reporting and analytics capabilities  
- No integration with external payment gateways  
- No multiple admins  

## 🚀 Future Roadmap

### Short-term Goals

- Add comprehensive unit testing  
- Implement offline caching for better connectivity handling  
- Add document management for property contracts  
- Enhance UI with animations and transitions  

### Long-term Vision

- Adding multiple admins section and proper contacting and transfer contracts  
- Expand to iOS using Kotlin Multiplatform  
- Implement advanced analytics dashboard  
- Add integrated chat functionality between admin and customers  
- Integrate with popular CRM systems for data synchronization  
- Add AI-powered recommendations for potential buyers  

## 📥 Installation

1. Download the APK from [here](https://drive.google.com/file/d/1nN3_P0IjP0si6kLQ_avVdUz10Wr4Zme0/view?usp=sharing)  
2. Enable installation from unknown sources in your Android settings  
3. Install the application  
4. Log in as admin or browse as a customer  

   For admin use username: admin and password: password  

## 🎬 Demo

Check out the [video demonstration](https://drive.google.com/file/d/1bSxdPi0EWtsKz7fS-Y0AjxyVrt2SG39F/view?usp=sharing) to see **PropVault** in action!

## 📋 Requirements

- Android 8.0 (API level 26) or higher  
- Internet connection  

---

<p align="center">
  Made with ❤️ by Prafull Kumar
</p>

