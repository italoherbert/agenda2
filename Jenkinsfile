pipeline {
	agent any
	
	stage( 'GIT Clone' ) {
		steps {
			echo 'Clonando repositório...'
		}
	}

	stage( 'Compile e package' ) {
		steps {
			echo 'Compilando e empacotando...'
		}
	}

	stage( 'Build docker image' ) {
		steps {
			echo 'Construindo imagem docker'
		}
	}
	
	stage( 'Implantação no kubernetes' ) {
		steps {
			echo 'Implantando no kubernetes'
		}
	}

}