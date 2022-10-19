import {Component, OnInit, Input, Injector, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-animations-dialog',
  templateUrl: './animations-dialog.component.html',
  styleUrls: ['./animations-dialog.component.css']
})
export class AnimationsDialogComponent implements OnInit {

  znodeContent: String = "no content";

  constructor(
    public dialogRef: MatDialogRef<AnimationsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data: String) {
    if ("" !== data) {
      this.znodeContent = data;
    }
  }

  ngOnInit(): void {
  }

}
