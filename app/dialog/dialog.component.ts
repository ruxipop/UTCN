import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
    selector: 'app-dialog',
    templateUrl: './dialog.component.html',
    styleUrls: ['./dialog.component.scss']
})
export class DialogComponent {
    prop_id: number = 0;
    user_username: string = ''

    constructor(public dialogRef: MatDialogRef<DialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
        // @ts-ignore
        this.user_username = localStorage.getItem('username')
        this.prop_id = parseInt(data.id)

    }

    closeDialog() {

        const modalContainer = document.querySelector('.modal-container') as HTMLElement;
        const app = document.querySelector('.booking-form') as HTMLElement;

        modalContainer.style.display = 'none';
        app.style.display = 'none';

    }
}
