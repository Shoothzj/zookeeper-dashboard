import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NodesService} from "../service/nodes.service";
import {NodesDataSource} from "../service/nodes.datasource";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'zookeeper-dashboard';

  displayedColumns = ["node"];

  dataSource: NodesDataSource;

  znodeParentPath: string = "/";

  constructor(private http: HttpClient, private nodesService: NodesService) {
    this.dataSource = new NodesDataSource(this.nodesService);
  }

  ngOnInit() {
    this.dataSource.getNodes("/");
  }

  onRowClicked(row: any) {
    this.znodeParentPath = "/" + row;
    this.dataSource.getNodes(this.znodeParentPath)
  }
}
