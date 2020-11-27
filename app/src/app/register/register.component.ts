import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  focus: any;
  save(form: NgForm) {
    console.log(form);
    form.reset();
  }
  constructor() {}

  ngOnInit(): void {}
}
