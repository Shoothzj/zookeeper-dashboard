import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {BehaviorSubject, Observable} from "rxjs";
import {NodesService} from "./nodes.service";

export class NodesDataSource implements DataSource<String> {

  private nodesSubject = new BehaviorSubject<String[]>([]);

  private loadingNodes = new BehaviorSubject<boolean>(false);

  public loading$ = this.loadingNodes.asObservable();

  constructor(private nodesService: NodesService) {}

  connect(collectionViewer: CollectionViewer): Observable<String[]> {
    return this.nodesSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.nodesSubject.complete();
    this.loadingNodes.complete();
  }

  getNodes(path: String) {
    this.loadingNodes.next(true);
    this.nodesService.getNodes(path).subscribe((nodes: String[]) => {
      this.nodesSubject.next(nodes);
      this.loadingNodes.next(false);
    });
  }

}
