package pl.edu.icm.coansys.metaextr.metadata.zoneclassification.features;

import pl.edu.icm.coansys.metaextr.tools.classification.features.FeatureCalculator;
import pl.edu.icm.coansys.metaextr.structure.model.BxPage;
import pl.edu.icm.coansys.metaextr.structure.model.BxZone;

/**
 *
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class BibinfoFeature implements FeatureCalculator<BxZone, BxPage> {

    private static String featureName = "Bibinfo";

    @Override
    public String getFeatureName() {
        return featureName;
    }

    @Override
    public double calculateFeatureValue(BxZone zone, BxPage page) {

        String[] keywords = {"cite", "pages", "article", "volume", "publishing", "journal", "doi", "cite this article",
                             "citation", "issue", "issn"};

        int count = 0;
        for (String keyword : keywords) {
            if (zone.toText().toLowerCase().contains(keyword)) {
                count++;
            }
        }

        return count;
    }

}