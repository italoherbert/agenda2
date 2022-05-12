pipeline {
	agent any
	
	stages {		
		stage( 'Inicialização' ) {						
			steps {
				script {
					def dockerHome = tool 'JenkinsDocker'	
					env.PATH = "${dockerHome}/bin:${env.PATH}"
				}				
			}			
		}		

		stage( 'GIT Clone' ) {
			environment {
				GITHUB_CREDENCIAIS = credentials( 'github' )
			}
			steps {
				sh "if [ -d \"agenda2/\" ]; then rm -r agenda2/; fi"
				sh "git clone https://${GITHUB_CREDENCIAIS_PSW}@github.com/italoherbert/agenda2.git"
			}
		}

		stage( 'Compile e package' ) {
			steps {
				dir( 'agenda2/agenda-pessoa' ) {
					sh "chmod 777 mvnw"
					sh "./mvnw clean package -DskipTests"
				}
				dir( 'agenda2/agenda-log' ) {
					sh "chmod 777 mvnw"
					sh "./mvnw clean package -DskipTests"
				}
			}
		}

		stage( 'Build docker image' ) {			
			steps {
				dir( 'agenda2/agenda-pessoa' ) {
					sh "docker build -t italoherbert/agenda-pessoa:latest ."
				}
				dir( 'agenda2/agenda-log' ) {
					sh "docker build -t italoherbert/agenda-log:latest ."
				}
			}
		}
		
		stage( 'Docker hub login' ) {
			environment {
				DOCKER_HUB_CREDENCIAIS = credentials( 'docker-hub' )
			}
			steps {
				sh 'docker login -u $DOCKER_HUB_CREDENCIAIS_USR -p $DOCKER_HUB_CREDENCIAIS_PSW'					
			}
		}
		
		stage( 'Docker image push' ) {
			steps {
				sh "docker push italoherbert/agenda-pessoa:latest"
				sh "docker push italoherbert/agenda-log:latest"
			}
		}
		
		stage( 'Implantação no kubernetes' ) {
			steps {
				dir( 'agenda2/kube' ) {
					withKubeConfig([credentialsId: 'client.key']) {						  
						sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
						sh 'chmod u+x ./kubectl'
						sh './kubectl apply -f agenda-pessoa.yaml'
						sh './kubectl apply -f agenda-log.yaml'
						sh './kubectl get pods'
					}
				}
			}
		}	
	}
}