import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

import {Router} from "@angular/router";
import {AuthService} from "../services/auth.service";

@Component({
    selector: 'app-register-page',
    templateUrl: './register-page.component.html',
    styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent {
    error2: boolean = false;
    existUsername: boolean = false;
    existEmail: boolean = false;
    form = new FormGroup({
        first_name: new FormControl(null, Validators.required),
        last_name: new FormControl(null, Validators.required),
        email: new FormControl(null, Validators.email),
        username: new FormControl(null, Validators.required),
        password: new FormControl(null, Validators.required)
    })

    constructor(private service: AuthService, private router: Router) {


    }

    onSubmit() {
        this.existEmail = false
        this.existUsername = false
        if (this.form.invalid) {
            this.error2 = true
            return;
        }


        //@ts-ignore
        this.service.registerUser(this.form.value)
            .subscribe((result) => {
                    if (result != null) {
                        this.router.navigateByUrl('login-page').then(r => null)
                    }
                },
                error => {
                    if (error.error.username) {
                        this.existUsername = true
                    } else {
                        this.existEmail = true
                    }

                });


    }
}
