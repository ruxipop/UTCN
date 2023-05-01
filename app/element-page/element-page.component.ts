import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PropertyService} from "../services/property.service";
import {Image} from "../models/image";
import {Property} from "../models/property";
import {MatDialog} from "@angular/material/dialog";
import {DialogComponent} from "../dialog/dialog.component";

@Component({
    selector: 'app-element-page',
    templateUrl: './element-page.component.html',
    styleUrls: ['./element-page.component.scss']
})
export class ElementPageComponent implements OnInit {


    currentId: number = 0;
    images: Image[] = [];

    whichImage: string = '';
    stringImages: string[] = []
    //@ts-ignore
    currentProperty: Property;
    currentSlide = 0;
    opern: boolean = false;

    constructor(private route: ActivatedRoute, private service: PropertyService, private dialog: MatDialog) {
        this.currentProperty = this.service.currentProperty;

        this.route.params.subscribe(params => {
            this.currentId = params['id'];

        });
        if (this.currentId === null) {
            //@ts-ignore

            this.currentId = parseInt(localStorage.getItem('selected'))
        }
        this.getImage();

    }

    ngOnInit(): void {
        if (this.currentId === null) {
            //@ts-ignore
            console.log("Ss", parseInt(localStorage.getItem('selected')))
            //@ts-ignore

            this.currentId = parseInt(localStorage.getItem('selected'))
        }
        throw new Error('Method not implemented.');
    }

    isAdmin() {

        // @ts-ignore
        console.log(parseInt(localStorage.getItem('role')))
        // @ts-ignore
        return parseInt(localStorage.getItem('role')) === 0;

    }

    getImage() {
        this.service.getImageProperty(this.currentId).subscribe(data => {
            this.images = data;
            this.getImages();
        });
    }

    getImages() {
        this.images.forEach((item) => {
            console.log(item)
            this.stringImages.push(item.image);
        });

        console.log(this.stringImages)
    }

    nextSlide() {
        if (this.currentSlide < this.images.length - 1) {
            this.currentSlide++;
        } else {
            this.currentSlide = 0;
        }
    }

    prevSlide() {
        if (this.currentSlide > 0) {
            this.currentSlide--;
        } else {
            this.currentSlide = this.images.length - 1;
        }
    }

    isNullFloorplan() {
        return this.currentProperty.floorplan === "''"
    }

    openModal(imageType: string) {
        if (imageType === 'floorplan') {
            this.whichImage = this.currentProperty.floorplan;
            const modal = document.getElementById('modal');
            // @ts-ignore
            modal.classList.remove('modal-epc');
        } else if (imageType === 'epc') {
            this.whichImage = this.currentProperty.epc;
            const modal = document.getElementById('modal');
            // @ts-ignore
            modal.classList.add('modal-epc');
        }
        const modal = document.getElementById('modal');
        // @ts-ignore
        modal.style.display = 'block';
    }

    closeModal() {
        console.log("aj")
        const modal = document.getElementById('modal');
        // @ts-ignore
        modal.style.display = 'none';
    }

    openDialog() {
        const dialogRef = this.dialog.open(DialogComponent, {
            width: '500px',
            data: {id: this.currentId}
        });

    }


    openDialogCalendar(id: string) {
        this.opern = true;
        setTimeout(() => {
            const element = document.getElementById(id);
            if (element) {
                element.scrollIntoView({behavior: 'smooth'});
            }
        }, 500); // așteaptă 1 secundă înainte de a face scroll
    }


}
