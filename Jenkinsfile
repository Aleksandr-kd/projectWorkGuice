pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Start Selenium Grid') {
            steps {
                sh 'docker-compose up -d selenium-hub chrome firefox'
                sh 'sleep 10'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'mvn clean test -Dbrowser=chrome -Dmode=grid -Dgrid.url=http://selenium-hub:4444/wd/hub'
                sh 'mvn clean test -Dbrowser=firefox -Dmode=grid -Dgrid.url=http://selenium-hub:4444/wd/hub'
            }
        }
        stage('Publish Allure Report') {
            steps {
                sh 'allure generate target/allure-results --clean -o target/allure-report || echo "Allure report generation failed"'
                publishHTML([
                    reportName: 'Allure Report',
                    reportDir: 'target/allure-report',
                    reportFiles: 'index.html'
                ])
            }
        }
    }
    post {
        always {
            sh 'docker-compose down'
        }
    }
}