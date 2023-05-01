import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
    selector: 'app-dialog-content',
    templateUrl: './dialog-content.component.html',
    styleUrls: ['./dialog-content.component.scss']
})
export class DialogContentComponent {
    propertyEdited: boolean = false;


    constructor(public dialogRef: MatDialogRef<DialogContentComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {

    }

    closeDialog() {

        const modalContainer = document.querySelector('.modal-container') as HTMLElement;


        modalContainer.style.display = 'none';


    }
}
