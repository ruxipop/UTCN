import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {MatDialog} from "@angular/material/dialog";

interface User {
    username: string;
    password: string;
}

@Component({
    selector: 'app-login-page',
    templateUrl: './login-page.component.html',
    styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent {

    responsedData: any;

    form = new FormGroup({
        username: new FormControl(null, Validators.required),
        password: new FormControl(null, Validators.required)
    })
    error = false;
    error2 = false;

    constructor(private authService: AuthService, private router: Router, private dialog: MatDialog) {
        console.log('Constructor called');
    }


    onSubmit() {
        if (this.form.invalid) {
            this.error2 = true;
            this.error = false;
            return;
        }

        //@ts-ignore
        this.authService.login(this.form.value).subscribe(
            (result) => {
                if (result != null) {
                    this.responsedData = result;
                    // @ts-ignore
                    localStorage.setItem('pass', this.form.value.password);
                    window.sessionStorage.setItem('token',this.responsedData.access)
                    localStorage.setItem('role', this.responsedData.authenticatedUser.role)
                    this.router.navigate(['']).then((r) => null);
                }
            },
            (error) => {
                this.error = true;
                this.error2 = false;
            }
        );
    }


}