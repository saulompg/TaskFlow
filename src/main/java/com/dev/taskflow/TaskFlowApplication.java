package com.dev.taskflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class TaskFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskFlowApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner demo(TaskRepository repository) {
//		return (args) -> {
//			// Criando uma tarefa
//			Task t = new Task();
//			t.setTitle("Estudar Java");
//			t.setDescription("Revisar conceitos de Collections, Stream e Exceptions");
//
//			// Salvando no banco (o insert SQL é gerado automaticamente)
//			repository.save(t);
//
//			System.out.println("Tarefa salva com ID: " + t.getId());
//
//			// Buscando todas
//			repository.findAll().forEach(tarefa ->
//					System.out.println("No banco: " + tarefa.getTitle())
//			);
//		};
//	}

}
