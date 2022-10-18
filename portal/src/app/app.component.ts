import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
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

  znodePath: string = "/";

  znodeContent: String = "";

  constructor(private http: HttpClient, private nodesService: NodesService) {
    this.dataSource = new NodesDataSource(this.nodesService);
  }

  ngOnInit() {
    this.dataSource.getNodes("/");
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
}
