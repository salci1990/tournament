package tournament.aaa;

import tournament.aaa.chart.Chart;
import tournament.aaa.nodeStructure.LastNode;
import tournament.aaa.nodeStructure.NodeStructure;
import tournament.constants.Constants;
import tournament.model.Game;
import tournament.model.Tournament;

import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class TreeStructure {

    private Chart chart = new Chart();
    private NodeStructure nodeStructure;

    public TreeStructure(Tournament tournament) {
        List<Game> sortedGames = tournament.getGames().stream()
                .sorted(comparingInt(Game::getRound).thenComparingInt(Game::getGameNumber))
                .collect(toList());
        List<NodeStructure> nodeStructures = new ArrayList<>();
        for (int i = 0; i < sortedGames.size(); i++) {
            nodeStructures.add(new NodeStructure());
        }
        for (int i = 0; i < nodeStructures.size()/2; i++) {
            NodeStructure node = setGameNoAndRound(nodeStructures.get(2*i + 1), sortedGames.get(i));
            nodeStructures.get(i).getChildren()[0] = node;
            node = setGameNoAndRound(nodeStructures.get(2*i + 2), sortedGames.get(i));
            nodeStructures.get(i).getChildren()[1] = node;
//            nodeStructures.get(i).getChildren()[0] = nodeStructures.get(2*i + 1);
//            nodeStructures.get(i).getChildren()[1] = nodeStructures.get(2*i + 2);
        }
        for (int i = 0; i < nodeStructures.size(); i++) {
            nodeStructures.get(i).setName(sortedGames.get(i).getWinner());
        }
        for (int i = (nodeStructures.size() / 2); i < nodeStructures.size(); i++) {
            LastNode node = setGameNoAndRound(new LastNode(sortedGames.get(i).getWhite().getNick(),"first"),
                    sortedGames.get(i));
            nodeStructures.get(i).getChildren()[0] = node;
            if (sortedGames.get(i).getBlack() != null)
                node = setGameNoAndRound(new LastNode(sortedGames.get(i).getBlack().getNick(), "first"),
                        sortedGames.get(i));
            else node = setGameNoAndRound(new LastNode(Constants.WILDCARD, "first bye"),
                    sortedGames.get(i));
            nodeStructures.get(i).getChildren()[1] = node;
//            nodeStructures.get(i).getChildren()[0] = new LastNode(sortedGames.get(i).getWhite().getNick(), "first");
//            if (sortedGames.get(i).getBlack() != null)
//                nodeStructures.get(i).getChildren()[1] = new LastNode(sortedGames.get(i).getBlack().getNick(), "first");
//            else nodeStructures.get(i).getChildren()[1] = new LastNode(Constants.WILDCARD, "first bye");
        }
        nodeStructures.get(0).setHtmlClass("winner");
        this.nodeStructure = nodeStructures.get(0);
    }

    private NodeStructure setGameNoAndRound(NodeStructure node, Game game) {
        node.getText().setDataGame(game.getGameNumber());
        node.getText().setDataRound(game.getRound());
        return node;
    }

    private LastNode setGameNoAndRound(LastNode node, Game game) {
        node.getText().setDataGame(game.getGameNumber());
        node.getText().setDataRound(game.getRound());
        return node;
    }

    public Chart getChart() {
        return chart;
    }

    public NodeStructure getNodeStructure() {
        return nodeStructure;
    }
}
