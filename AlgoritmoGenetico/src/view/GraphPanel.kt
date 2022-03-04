package view

import java.awt.*
import javax.swing.JFrame
import javax.swing.JPanel


fun createAndShowGraph(title: String, scores: List<Double>, location: Point?=null): JFrame {
    val mainPanel = GraphPanel(scores)
    mainPanel.preferredSize = Dimension(850, 600)
    val frame = JFrame(title)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.contentPane.add(mainPanel)
    frame.pack()
    frame.setLocationRelativeTo(null)
    if (location != null) {
        frame.location = location
    }
    frame.isVisible = true

    return frame
}

class GraphPanel(private var scores: List<Double>): JPanel() {

    private val padding = 25
    private val labelPadding = 25
    private val lineColor = Color(44, 102, 230, 180)
    private val pointColor = Color(100, 100, 100, 180)
    private val gridColor = Color(200, 200, 200, 200)
    private val GRAPH_STROKE: Stroke = BasicStroke(2f)
    private val pointWidth = 4
    private val numberYDivisions = 10

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2 = g as Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        val xScale = (width.toDouble() - 2 * padding - labelPadding) / (scores.size - 1)
        val yScale = (height.toDouble() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore())
        val graphPoints: MutableList<Point> = mutableListOf()
        for (i in scores.indices) {
            val x1 = (i * xScale + padding + labelPadding).toInt()
            val y1 = ((getMaxScore() - scores[i]) * yScale + padding).toInt()
            graphPoints.add(Point(x1, y1))
        }

        g2.color = Color.WHITE
        g2.fillRect(
            padding + labelPadding,
            padding,
            width - 2 * padding - labelPadding,
            height - 2 * padding - labelPadding
        )
        g2.color = Color.BLACK

        for (i in 0 until numberYDivisions + 1) {
            val x0 = padding + labelPadding
            val x1 = pointWidth + padding + labelPadding
            val y0 = height - (i * (height - padding * 2 - labelPadding) / numberYDivisions + padding + labelPadding)
            if (scores.isNotEmpty()) {
                g2.color = gridColor
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, width - padding, y0)
                g2.color = Color.BLACK
                val yLabel = (((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100).toInt() / 100.0).toString()
                val metrics = g2.fontMetrics
                val labelWidth = metrics.stringWidth(yLabel)
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + metrics.height / 2 - 3)
            }
            g2.drawLine(x0, y0, x1, y0)
        }

        for (i in scores.indices) {
            if (scores.size > 1) {
                val x0 = i * (width - padding * 2 - labelPadding) / (scores.size - 1) + padding + labelPadding
                val y0 = height - padding - labelPadding
                val y1 = y0 - pointWidth
                if (i % ((scores.size / 20.0).toInt() + 1) == 0) {
                    g2.color = gridColor
                    g2.drawLine(x0, height - padding - labelPadding - 1 - pointWidth, x0, padding)
                    g2.color = Color.BLACK
                    val xLabel = i.toString() + ""
                    val metrics = g2.fontMetrics
                    val labelWidth = metrics.stringWidth(xLabel)
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.height + 3)
                }
                g2.drawLine(x0, y0, x0, y1)
            }
        }

        g2.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding)
        g2.drawLine(
            padding + labelPadding,
            height - padding - labelPadding,
            width - padding,
            height - padding - labelPadding
        )
        val oldStroke = g2.stroke
        g2.color = lineColor
        g2.stroke = GRAPH_STROKE
        for (i in 0 until graphPoints.size - 1) {
            val x1: Int = graphPoints[i].x
            val y1: Int = graphPoints[i].y
            val x2: Int = graphPoints[i + 1].x
            val y2: Int = graphPoints[i + 1].y
            g2.drawLine(x1, y1, x2, y2)
        }
        g2.stroke = oldStroke
        g2.color = pointColor
        for (i in graphPoints.indices) {
            val x: Int = graphPoints[i].x - pointWidth / 2
            val y: Int = graphPoints[i].y - pointWidth / 2
            val ovalW = pointWidth
            val ovalH = pointWidth
            g2.fillOval(x, y, ovalW, ovalH)
        }
    }

    private fun getMinScore(): Double {
        var minScore = Double.MAX_VALUE
        for (score in scores) {
            minScore = minScore.coerceAtMost(score)
        }
        return minScore
    }

    private fun getMaxScore(): Double {
        var maxScore = Double.MIN_VALUE
        for (score in scores) {
            maxScore = maxScore.coerceAtLeast(score)
        }
        return maxScore
    }

    fun changeScores(scores: List<Double>) {
        this.scores = scores
        invalidate()
        this.repaint()
    }
}