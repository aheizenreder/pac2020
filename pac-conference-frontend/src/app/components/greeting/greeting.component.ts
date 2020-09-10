import { Component, OnInit } from "@angular/core";
import { GreetingControllerService } from "../../services/backend-api/api/greetingController.service";
import { FormControl } from "@angular/forms";

@Component({
  selector: "app-greeting",
  templateUrl: "./greeting.component.html",
  styleUrls: ["./greeting.component.css"],
})
export class GreetingComponent implements OnInit {
  constructor(
    private readonly greetingControllerService: GreetingControllerService
  ) {}

  greeting: String = "";
  name = new FormControl("");

  ngOnInit() {}

  greet() {
    this.loadGreeting(this.name.value);
  }

  loadGreeting(userName) {
    // subscribe to rest API Observable to get async answer of Spring Boot API
    return this.greetingControllerService
      .greeting(userName)
      .subscribe((data) => {
        this.greeting = data;
      });
  }
}
