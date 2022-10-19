import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NodesService} from "../service/nodes.service";
import {NodesDataSource} from "../service/nodes.datasource";
import {MatDialog} from "@angular/material/dialog";
import {AnimationsDialogComponent} from "./animations-dialog/animations-dialog.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'zookeeper-dashboard';

  displayedColumns = ["node"];

  dataSource: NodesDataSource;

  znodePath: string = "/";

  znodeContent: String = "no content.";

  DecodeContent: String = "";

  decodeComponent = 'None';

  decodeNamespace = 'None';

  supportDecodeComponents: String[] = [];

  supportDecodeNamespaces: String[] = [];

  constructor(private http: HttpClient, private nodesService: NodesService, public dialog: MatDialog) {
    this.dataSource = new NodesDataSource(this.nodesService);
  }

  ngOnInit() {
    this.dataSource.getNodes("/");
    this.nodesService.getDecodeComponents()
      .pipe().subscribe((data: String[]) => this.supportDecodeComponents = data);
    this.nodesService.getDecodeNamespaces()
      .pipe().subscribe((data: String[]) => this.supportDecodeNamespaces = data);
  }

  homePageClicked() {
    this.znodePath = "/";
    this.znodeContent = ""
    this.dataSource.getNodes(this.znodePath)
  }

  onRowClicked(row: any) {
    if (this.znodePath == "/") {
      this.znodePath = "/" + row;
    } else {
      this.znodePath = this.znodePath + "/" + row;
    }
    this.dataSource.getNodes(this.znodePath)
    this.nodesService.getNodeContent(this.znodePath).subscribe(data => {
      this.znodeContent = data;
    });
  }

  onDecodeClicked() {
    this.nodesService.getNodeDecodeContent(this.znodePath, this.decodeComponent, this.decodeNamespace).subscribe(data => {
      this.DecodeContent = data;
    })
  }

  openDialog(enterAnimationDuration: string, exitAnimationDuration: string): void {
    this.nodesService.getNodeHexContent(this.znodePath).subscribe(data => {
      this.dialog.open(AnimationsDialogComponent, {
        width: '1000px',
        enterAnimationDuration,
        exitAnimationDuration,
        data: data
      });
    });
  }
}
