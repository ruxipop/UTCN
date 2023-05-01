import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Property} from "../models/property";
import {PropertyService} from "../services/property.service";

@Component({
    selector: 'app-edit-property',
    templateUrl: './edit-property.component.html',
    styleUrls: ['./edit-property.component.scss']
})
export class EditPropertyComponent implements OnInit {
    currentProperty: Property = {
        id: 0,
        name: '',
        address: '',
        description: '',
        price: '',
        lot_area: '',
        isBuy: false,
        nb_bath: 0,
        nb_bed: 0,
        nb_garage: 0,

        year_build: 0,
        type: '',
        epc: '',
        garden: false,
        floorplan: '',
        images: []
    }
    @Output() propertyEdited = new EventEmitter<void>();


    @Input()
    propertyID: number = 0;
    form = new FormGroup({
        name: new FormControl(null, Validators.required),
        address: new FormControl(null, Validators.required),
        description: new FormControl(null, Validators.required),
        price: new FormControl(null, Validators.required),
        lot_area: new FormControl(null, Validators.required),
        // garden: new FormControl(null, Validators.required),
        isBuy: new FormControl(null, Validators.required),
        nb_bath: new FormControl(null, Validators.required),
        nb_bed: new FormControl(null, Validators.required),
        nb_garage: new FormControl(null, Validators.required),
        year_build: new FormControl(null, Validators.required),
        type: new FormControl(null, Validators.required),

    })

    constructor(private service: PropertyService) {
    }

    onSubmit() {

        if (this.form.invalid) {
            return
        }


        this.service.updateProperty(this.currentProperty).subscribe(() => {
            window.location.reload();
        });


    }

    isBuy() {
        if (this.currentProperty.isBuy) {
            return 0
        }
        return 1
    }

    ngOnInit() {

        this.service.getPropertyByID(this.propertyID).subscribe(
            (data) => {
                console.log(data)
                this.currentProperty = data
            }
        )
    }
}
