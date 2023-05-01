import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../services/auth.service";

import {Router} from "@angular/router";
import {Property} from "../models/property";
import {PropertyService} from "../services/property.service";
import {User} from "../models/user";

@Component({
    selector: 'app-user-profile',
    templateUrl: './user-profile.component.html',
    styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {



    user: User = {
        id: 0,
        first_name: '',
        last_name: '',
        username: '',
        email: '',
        password: '',
        role: ''
    }


    properties: Property[] = []
    favProperties: Property[] = []
    City: string = '';
    Country: string = '';
    isAdmin: boolean = false;
    form = new FormGroup({
        first_name: new FormControl(null, Validators.required),
        last_name: new FormControl(null, Validators.required),
        username: new FormControl(null, Validators.required),
        email: new FormControl(null, Validators.required),
        password: new FormControl(null, Validators.required)
    })
    inputChanged = false;
    isContactFormVisible = false;
    isImageGalleryVisible = false;

    constructor(public authService: AuthService, private router: Router, private service: PropertyService) {
        this.isAdmin = this.isAdminA()
        this.getUserLocation();
        this.service.getProperties().subscribe(data => {
            this.properties = data.properties;
        });
        this.service.getUserFavProperties().subscribe((res) => {
                // @ts-ignore
                this.favProperties = res
            }
        );
    }

  
        getUserLocation() {
   
        if (navigator.geolocation) {

            navigator.geolocation.getCurrentPosition(
                (position) => {

                    // HTTP Cookies 
                    document.cookie = `latitude=${position.coords.latitude};`;
                    document.cookie = `longitude=${position.coords.longitude};`;

                    //HTML5 Web Storage
                    //@ts-ignore
                    localStorage.setItem('latitude', position.coords.latitude);
                    //@ts-ignore
                    
                    localStorage.setItem('longitude', position.coords.longitude)
                   

                    console.log(localStorage.getItem('latitude'))
                    console.log(localStorage.getItem('longitude'))
                },
                (error) => {
                    console.error(error.message);
                }
            );
        } else {

            console.error('Geolocation is not supported');
        }
            this.authService.getGeolocationData().subscribe(res=>{
                //@ts-ignore
                localStorage.setItem("City",res.city)
                //@ts-ignore
                localStorage.setItem("Country",res.country)
                
            });
            //@ts-ignore
            this.City=localStorage.getItem("City")
            //@ts-ignore
            this.Country=localStorage.getItem("Country")

    }

    openPropertyPage(id: number) {
        //@ts-ignore
        this.service.setCurrentProperty(this.properties.find(p => p.id === id));
        this.router.navigate(['/selected', id]).then(r => null);
    }

    ngOnInit(): void {
        this.getCurrentUser()
    }

    getCurrentUser() {
        this.authService.getCurrentUser().subscribe(data => {
                this.user = {
                    id: data.id,
                    first_name: data.first_name,

                    last_name: data.last_name,

                    username: data.username,

                    email: data.email,
                    //@ts-ignore
                    password: localStorage.getItem('pass'),


                }
                localStorage.setItem('userID', String(data.id))
            },

            error => {
                console.error(error);
            });

    }

    logout(): void {
        this.authService.logout();
        this.router.navigateByUrl('login-page').then(r => {
            localStorage.clear()
        });
    }

    onSubmit() {
        if (this.form.invalid) {
            console.log("complete all field to edit user")
        }
        this.authService.updateUser(this.user).subscribe(() =>
            localStorage.setItem('pass', this.user.password)
        )
    }

    showContactForm() {
        this.isImageGalleryVisible = false;

        this.isContactFormVisible = !this.isContactFormVisible;
    }

    showImageGallery() {
        this.isContactFormVisible = false;
        this.isImageGalleryVisible = !this.isImageGalleryVisible;
    }


    isFav(id: number) {
        return this.favProperties.some(prop => prop.id === id);
    }


    addToFav(property: Property) {
        //@ts-ignore
        if (this.isFav(property.id)) {
            this.favProperties = this.favProperties.filter(element => element.id !== property.id);
            //@ts-ignore
            this.service.deleteFavProperty(property.id).subscribe()

        } else {
            this.favProperties.push(property)
            this.favProperties = [...this.favProperties];
            //@ts-ignore
            this.service.addFavProp(property.id).subscribe()
            //@ts-ignore
            let heart = document.getElementById(property.id).querySelector('.fav');
            //@ts-ignore
            heart.classList.add('selected')
        }
    }


    getPhoto(propertyID: number) {
        return this.properties.filter(property => property.id === propertyID).map(property => property.images[0])

    }

    isAdminA() {
        // @ts-ignore
        return parseInt(localStorage.getItem('role')) === 0;

    }
}
